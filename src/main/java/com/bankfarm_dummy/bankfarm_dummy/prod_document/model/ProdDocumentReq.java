package com.bankfarm_dummy.bankfarm_dummy.prod_document.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProdDocumentReq {
    private long branId;
    private String docProdTp;
    private long docProdId;
    private String docNm;
}
