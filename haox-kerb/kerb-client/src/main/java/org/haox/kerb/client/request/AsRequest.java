package org.haox.kerb.client.request;

import org.haox.kerb.client.KrbContext;
import org.haox.kerb.client.preauth.PreauthCallback;
import org.haox.kerb.spec.KrbConstant;
import org.haox.kerb.spec.KrbException;
import org.haox.kerb.spec.type.common.*;
import org.haox.kerb.spec.type.kdc.*;
import org.haox.kerb.spec.type.ticket.TgtTicket;

import java.io.IOException;
import java.util.List;

public class AsRequest extends KdcRequest {

    private PrincipalName clientPrincipal;
    private EncryptionKey clientKey;

    public AsRequest(KrbContext context) {
        super(context);

        setServerPrincipal(makeTgsPrincipal());
    }

    @Override
    protected PreauthCallback getPreauthCallback() {
        return new PreauthCallback() {
            public EncryptionKey getAsKey() throws KrbException {
                return getClientKey();
            }

            public void setAsKey(EncryptionKey asKey) {
                setClientKey(asKey);
            }

            @Override
            public String askFor(String question, String challenge) {
                return AsRequest.this.askFor(question, challenge);
            }

            @Override
            public Object getCacheValue(String key) {
                return credCache.get(key);
            }

            @Override
            public void cacheValue(String key, Object value) {
                credCache.put(key, value);
            }

            @Override
            public PrincipalName getClientPrincipal() {
                return AsRequest.this.getClientPrincipal();
            }
        };
    }

    public PrincipalName getClientPrincipal() {
        return clientPrincipal;
    }

    public void setClientPrincipal(PrincipalName clientPrincipal) {
        this.clientPrincipal = clientPrincipal;
    }

    public void setClientKey(EncryptionKey clientKey) {
        this.clientKey = clientKey;
    }

    public EncryptionKey getClientKey() throws KrbException {
        return clientKey;
    }

    @Override
    public void process() throws KrbException {
        super.process();

        KdcReqBody body = makeReqBody();

        AsReq asReq = new AsReq();
        asReq.setReqBody(body);
        asReq.setPaData(getPreauthData());

        setKdcReq(asReq);
    }

    @Override
    public void processResponse(KdcRep kdcRep) throws KrbException  {
        setKdcRep(kdcRep);

        PrincipalName clientPrincipal = getKdcRep().getCname();
        String clientRealm = getKdcRep().getCrealm();
        clientPrincipal.setRealm(clientRealm);
        if (! clientPrincipal.equals(getClientPrincipal())) {
            throw new KrbException(KrbErrorCode.KDC_ERR_CLIENT_NAME_MISMATCH);
        }

        byte[] decryptedData = decryptWithClientKey(getKdcRep().getEncryptedEncPart(),
                KeyUsage.AS_REP_ENCPART);
        EncKdcRepPart encKdcRepPart = new EncAsRepPart();
        try {
            encKdcRepPart.decode(decryptedData);
        } catch (IOException e) {
            throw new KrbException("Failed to decode EncAsRepPart", e);
        }
        getKdcRep().setEncPart(encKdcRepPart);

        if (getChosenNonce() != encKdcRepPart.getNonce()) {
            throw new KrbException("Nonce didn't match");
        }

        PrincipalName serverPrincipal = encKdcRepPart.getSname();
        serverPrincipal.setRealm(encKdcRepPart.getSrealm());
        if (! serverPrincipal.equals(getServerPrincipal())) {
            throw new KrbException(KrbErrorCode.KDC_ERR_SERVER_NOMATCH);
        }

        HostAddresses hostAddresses = getHostAddresses();
        if (hostAddresses != null) {
            List<HostAddress> requestHosts = hostAddresses.getElements();
            if (!requestHosts.isEmpty()) {
                List<HostAddress> responseHosts = encKdcRepPart.getCaddr().getElements();
                for (HostAddress h : requestHosts) {
                    if (!responseHosts.contains(h)) {
                        throw new KrbException("Unexpected client host");
                    }
                }
            }
        }
    }

    public TgtTicket getTicket() {
        TgtTicket TgtTicket = new TgtTicket(getKdcRep().getTicket(),
                (EncAsRepPart) getKdcRep().getEncPart(), getKdcRep().getCname().getName());
        return TgtTicket;
    }

    private PrincipalName makeTgsPrincipal() {
        return new PrincipalName(KrbConstant.TGS_PRINCIPAL + "@" + getContext().getKdcRealm());
    }
}
