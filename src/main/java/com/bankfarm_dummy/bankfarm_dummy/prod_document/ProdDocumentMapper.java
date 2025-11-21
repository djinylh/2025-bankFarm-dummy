package com.bankfarm_dummy.bankfarm_dummy.prod_document;

import com.bankfarm_dummy.bankfarm_dummy.prod_document.model.ProdDocumentReq;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProdDocumentMapper {
    int prodDocumentJoin(ProdDocumentReq prodReq);
}
