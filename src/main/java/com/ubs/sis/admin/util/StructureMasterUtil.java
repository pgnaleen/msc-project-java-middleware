package com.ubs.sis.admin.util;

import com.ubs.commons.domain.BaseEntity;
import com.ubs.sis.admin.master.domain.StructureMaster;

import java.util.Locale;

public class StructureMasterUtil {
    /**
     * sorting from structure master
     *
     * @param s
     * @return
     */
    public static String getStructureMasterSortKey(String s) {
        if (s != null) {
            String replace = s.toLowerCase(Locale.ROOT).replace("_", "");
            String result = BaseEntity.FIELD_ID;
            if (replace.contains("shortname")) {
                result = StructureMaster.FIELD_SHORT_NAME;
            } else if (replace.contains("code")) {
                result = StructureMaster.FIELD_CODE;
            } else if (replace.contains("name")) {
                result = StructureMaster.FIELD_NAME;
            } else if (replace.contains("active")) {
                result = StructureMaster.FIELD_ACTIVE_STATUS;
            }
            return result;

        }
        return s;
    }
}
