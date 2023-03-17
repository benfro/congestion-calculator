package net.benfro.concalc.ruleapi.util;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public interface TollFreeDateCacheLoadingRule {
    /**
     * Optional implement to specify the dates stored in toll free date cache
     * @param year
     * @return
     */
    List<LocalDateTime> loadDatesToCacheRule(int year);
}
