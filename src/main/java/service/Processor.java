package service;

import org.springframework.lang.Nullable;

public interface Processor<I,O> {
    @Nullable
    O process(I var1) throws Exception;
}
