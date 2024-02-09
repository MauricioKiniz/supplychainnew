package com.mksistemas.supply.shared.library.query;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

public class QueryHelper {

    private QueryHelper() {}

    public static <T> Slice<T> processPage(List<T> list, Pageable pageable) {
        if (list.size() > pageable.getPageSize())
            return new SliceImpl<>(list.subList(0, pageable.getPageSize()), pageable, true);
        return new SliceImpl<>(list, pageable, false);
    }
}
