/**
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 *
 */
package org.apache.kerby.cms.type;

import org.apache.kerby.asn1.Asn1FieldInfo;
import org.apache.kerby.asn1.EnumType;
import org.apache.kerby.asn1.type.Asn1SequenceType;

/**
 * ExtendedCertificate ::= SEQUENCE {
 *   extendedCertificateInfo ExtendedCertificateInfo,
 *   signatureAlgorithm SignatureAlgorithmIdentifier,
 *   signature Signature
 * }
 */
public class ExtendedCertificate extends Asn1SequenceType {
    protected enum ExtendedCertificateField implements EnumType {
        EXTENDED_CERTIFICATE_INFO,
        SIGNATURE_ALGORITHMS,
        SIGNATURE;

        @Override
        public int getValue() {
            return ordinal();
        }

        @Override
        public String getName() {
            return name();
        }
    }

    static Asn1FieldInfo[] fieldInfos = new Asn1FieldInfo[] {
            new Asn1FieldInfo(ExtendedCertificateField.EXTENDED_CERTIFICATE_INFO, ExtendedCertificateInfo.class),
            new Asn1FieldInfo(ExtendedCertificateField.SIGNATURE_ALGORITHMS, SignatureAlgorithmIdentifier.class),
            new Asn1FieldInfo(ExtendedCertificateField.SIGNATURE, Signature.class)
    };

    public ExtendedCertificate() {
        super(fieldInfos);
    }

    public ExtendedCertificateInfo getExtendedCertificateInfo() {
        return getFieldAs(ExtendedCertificateField.EXTENDED_CERTIFICATE_INFO, ExtendedCertificateInfo.class);
    }

    public void setExtendedCertificateInfo(ExtendedCertificateInfo extendedCertificateInfo) {
        setFieldAs(ExtendedCertificateField.EXTENDED_CERTIFICATE_INFO, extendedCertificateInfo);
    }

    public SignatureAlgorithmIdentifier getSignatureAlgorithmIdentifier() {
        return getFieldAs(ExtendedCertificateField.SIGNATURE_ALGORITHMS, SignatureAlgorithmIdentifier.class);
    }

    public void setSignatureAlgorithmIdentifier(SignatureAlgorithmIdentifier signatureAlgorithmIdentifier) {
        setFieldAs(ExtendedCertificateField.SIGNATURE_ALGORITHMS, signatureAlgorithmIdentifier);
    }

    public Signature getSignature() {
        return getFieldAs(ExtendedCertificateField.SIGNATURE, Signature.class);
    }

    public void setSignature(Signature signature) {
        setFieldAs(ExtendedCertificateField.SIGNATURE, signature);
    }
}
