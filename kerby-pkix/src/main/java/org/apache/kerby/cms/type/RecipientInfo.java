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
import org.apache.kerby.asn1.ImplicitField;
import org.apache.kerby.asn1.type.Asn1Choice;

/**
 * RecipientInfo ::= CHOICE {
 *   ktri KeyTransRecipientInfo,
 *   kari [1] KeyAgreeRecipientInfo,
 *   kekri [2] KEKRecipientInfo,
 *   pwri [3] PasswordRecipientInfo,
 *   ori [4] OtherRecipientInfo }
 */
public class RecipientInfo extends Asn1Choice {
    protected enum RecipientInfoField implements EnumType {
        KTRI,
        KARI,
        KEKRI,
        PWRI,
        ORI;

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
            new Asn1FieldInfo(RecipientInfoField.KTRI, KeyTransRecipientInfo.class),
            new ImplicitField(RecipientInfoField.KARI, KeyAgreeRecipientInfo.class),
            new ImplicitField(RecipientInfoField.KEKRI, KEKRecipientInfo.class),
            new ImplicitField(RecipientInfoField.PWRI, PasswordRecipientInfo.class),
            new ImplicitField(RecipientInfoField.ORI, OtherRecipientInfo.class)
    };

    public RecipientInfo() {
        super(fieldInfos);
    }

    public KeyTransRecipientInfo getKtri() {
        return getChoiceValueAs(RecipientInfoField.KTRI, KeyTransRecipientInfo.class);
    }

    public void setKtri(KeyTransRecipientInfo ktri) {
        setChoiceValue(RecipientInfoField.KTRI, ktri);
    }

    public KeyAgreeRecipientInfo getKari() {
        return getChoiceValueAs(RecipientInfoField.KARI, KeyAgreeRecipientInfo.class);
    }

    public void setKari(KeyAgreeRecipientInfo kari) {
        setChoiceValue(RecipientInfoField.KARI, kari);
    }

    public KEKRecipientInfo getKekri() {
        return getChoiceValueAs(RecipientInfoField.KEKRI, KEKRecipientInfo.class);
    }

    public void setKekri(KEKRecipientInfo kekri) {
        setChoiceValue(RecipientInfoField.KEKRI, kekri);
    }

    public PasswordRecipientInfo getPwri() {
        return getChoiceValueAs(RecipientInfoField.PWRI, PasswordRecipientInfo.class);
    }

    public void setPwri(PasswordRecipientInfo pwri) {
        setChoiceValue(RecipientInfoField.PWRI, pwri);
    }

    public OtherRecipientInfo getori() {
        return getChoiceValueAs(RecipientInfoField.ORI, OtherRecipientInfo.class);
    }

    public void setOri(OtherRecipientInfo ori) {
        setChoiceValue(RecipientInfoField.ORI, ori);
    }
}
