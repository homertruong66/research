package com.hoang.lsp.service;

import com.hoang.lsp.core.GoalType;
import com.hoang.lsp.events.ClickEvent;
import com.hoang.lsp.events.ConversionEvent;
import com.hoang.lsp.exception.BusinessException;


public interface LinkStatsService {

    public void doIncreaseClick(ClickEvent clickEvent);

    public void doIncreaseConversion(ConversionEvent conversionEvent);

    public void loadClickStats() throws Exception;

    public void loadConversionStats(GoalType goalType) throws Exception;

    public void loadLastClickAt() throws Exception;

    public void increaseClick(ClickEvent clickEvent) throws BusinessException;

    public void increaseConversion(ConversionEvent conversionEvent) throws BusinessException;

}
