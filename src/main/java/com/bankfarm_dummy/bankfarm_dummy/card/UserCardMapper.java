package com.bankfarm_dummy.bankfarm_dummy.card;

import com.bankfarm_dummy.bankfarm_dummy.card.model.UserCardGetRes;
import com.bankfarm_dummy.bankfarm_dummy.card.model.UserCardReq;
import com.bankfarm_dummy.bankfarm_dummy.prod_document.model.ProdDocumentReq;

import java.util.List;

public interface UserCardMapper {

    int userCardJoin(UserCardReq req);
}
