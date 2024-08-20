package com.smartjob.auth.dqo;

import com.myzlab.k.allowed.KColumnAllowedToSelect;
import static com.smartjob.auth.k.generated.metadata.Tables.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppUserDQO {

    private static final Map<String, KColumnAllowedToSelect> SELECTS;
    
    static {
        SELECTS = new HashMap<>();
        SELECTS.put("au.id", APP_USER.ID);
        SELECTS.put("au.name", APP_USER.NAME);
        SELECTS.put("au.email", APP_USER.EMAIL);
    }
    
    public static Data toSelects(
        final String fields,
        final String orderBy
    ) {
        return toSelects(Arrays.asList(fields.split(",")), orderBy);
    }
    
    public static Data toSelects(
        final List<String> fields,
        final String orderBy
    ) {
        if (fields == null || fields.isEmpty()) {
            return null;
        }
        
        final List<KColumnAllowedToSelect> selects = new ArrayList();
        boolean applyWorkspaceMember = orderBy != null && (orderBy.startsWith("wm.") || orderBy.startsWith("wro."));
        boolean applyProjectMember = orderBy != null && (orderBy.startsWith("pm.") || orderBy.startsWith("pro."));
        boolean applyErmMember = orderBy != null && (orderBy.startsWith("em.") || orderBy.startsWith("ero."));
        boolean applyWorkspaceRole = orderBy != null && orderBy.startsWith("wro.");
        boolean applyProjectRole = orderBy != null && orderBy.startsWith("pro.");
        boolean applyErmRole = orderBy != null && orderBy.startsWith("ero.");
        
        for (final String field : fields) {
            final String fieldTrim = field.trim();
            final KColumnAllowedToSelect select = SELECTS.get(fieldTrim);
            
            if (select == null) {
                continue;
            }
            
            if (fieldTrim.startsWith("pm.")) {
                applyProjectMember = true;
            } else if (fieldTrim.startsWith("wm.")) {
                applyWorkspaceMember = true;
            } else if (fieldTrim.startsWith("em.")) {
                applyErmMember = true;
            } else if (fieldTrim.startsWith("wro.")) {
                applyWorkspaceMember = true;
                applyWorkspaceRole = true;
            } else if (fieldTrim.startsWith("pro.")) {
                applyProjectMember = true;
                applyProjectRole = true;
            } else if (fieldTrim.startsWith("ero.")) {
                applyErmMember = true;
                applyErmRole = true;
            }
            
            selects.add(select);
        }
        
        if (selects.isEmpty()) {
            return null;
        }
        
        return new Data(selects.toArray(KColumnAllowedToSelect[]::new), applyWorkspaceMember, applyProjectMember, applyErmMember, applyWorkspaceRole, applyProjectRole, applyErmRole);
    }
    
    @lombok.Data
    @lombok.AllArgsConstructor
    public static class Data {
        private KColumnAllowedToSelect[] selects;
        private boolean applyWorkspaceMember;
        private boolean applyProjectMember;
        private boolean applyErmMember;
        private boolean applyWorkspaceRole;
        private boolean applyProjectRole;
        private boolean applyErmRole;
    }
}
