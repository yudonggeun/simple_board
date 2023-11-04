package com.example.simple_board.config;

import org.springframework.core.MethodParameter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.BindingContext;
import org.springframework.web.reactive.result.method.HandlerMethodArgumentResolver;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

public class PageableHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return Pageable.class.equals(parameter.getParameterType());
    }

    @Override
    public Mono<Object> resolveArgument(MethodParameter parameter, BindingContext bindingContext, ServerWebExchange exchange) {
        PageableDefault metadata = parameter.getParameterAnnotation(PageableDefault.class);

        var request = exchange.getRequest();
        var param = request.getQueryParams();

        return Mono.just(PageRequest.of(
                getPage(param, metadata),
                getSize(param, metadata),
                getSort(param, metadata))
        );
    }

    private Sort getSort(MultiValueMap<String, String> param, PageableDefault metadata) {
        if (param.containsKey("sort"))
            return Sort.by(param
                    .getOrDefault("sort", List.of())
                    .toArray(new String[0])
            );
        return Sort.by(metadata.direction(), metadata.sort());
    }

    private int getSize(MultiValueMap<String, String> param, PageableDefault metadata) {
        if (param.containsKey("size")) return Integer.parseInt(param.getFirst("size"));
        return metadata.size();
    }

    private int getPage(MultiValueMap<String, String> param, PageableDefault metadata) {
        if (param.containsKey("page")) return Integer.parseInt(param.getFirst("page"));
        return metadata.page();
    }


}
