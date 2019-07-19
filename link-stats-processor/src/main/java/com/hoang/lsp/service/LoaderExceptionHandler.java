package com.hoang.lsp.service;

import java.util.List;

import com.hoang.lsp.exception.BusinessException;
import com.hoang.lsp.model.IncrementEvent;

public interface LoaderExceptionHandler {
    public void writeFailedEventsToFile (List<IncrementEvent> incrementEventList) throws BusinessException;
}
