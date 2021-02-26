package com.reset4.passlock.comparator;

import com.reset4.fourwork.datalayer.BusinessObject;
import com.reset4.passlock.businessobjects.PasswordInfoBO;

import java.util.Comparator;

/**
 * Created by ilkery on 2.01.2017.
 */
public class PasswordInfoComparator implements Comparator<BusinessObject> {
    @Override
    public int compare(BusinessObject bo1, BusinessObject bo2) {
        PasswordInfoBO passwordInfoBO1 = (PasswordInfoBO) bo1;
        PasswordInfoBO passwordInfoBO2 = (PasswordInfoBO) bo2;
        String accountName1 = passwordInfoBO1.getEntity().getAccountName();
        String accountName2 = passwordInfoBO2.getEntity().getAccountName();
        return accountName1.compareToIgnoreCase(accountName2);
    }
}
