package com.itmuch.contentcenter.sentineltest;

import com.alibaba.csp.sentinel.adapter.servlet.callback.UrlBlockHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import com.alibaba.csp.sentinel.slots.system.SystemBlockException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description: 区分不同情况的异常并给出相应处理 比 限流 降级 系统异常等
 * @Author: zhangchao
 * @Date: 2020-01-05 20:33
 **/
@Component
public class MyUrlBlockHandler implements UrlBlockHandler {

    @Override
    public void blocked(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, BlockException e) throws IOException {
        ErrMsg msg = null;
        //限流异常
        if (e instanceof FlowException)
        {
            msg = ErrMsg.builder().status(100).msg("限流了...").build();
        }
        //降级异常
        else if (e instanceof DegradeException)
        {
            msg = ErrMsg.builder().status(101).msg("降级了...").build();

        }
        //参数规则异常
        else if (e instanceof ParamFlowException)
        {
            msg = ErrMsg.builder().status(102).msg("热点参数限流...").build();
        }
        //系统规则异常
        else if (e instanceof SystemBlockException)
        {
            msg = ErrMsg.builder().status(103).msg("系统负载不满足要求...").build();
        }
        //授权异常
        else if (e instanceof AuthorityException)
        {
            msg = ErrMsg.builder().status(104).msg("授权规则不通过...").build();
        }

        httpServletResponse.setStatus(500);
        httpServletResponse.setCharacterEncoding("utf-8");
        httpServletResponse.setHeader("Content-Type", "application/json;charset=utf-8");
        httpServletResponse.setContentType("application/json;charset=utf-8");
        new ObjectMapper().writeValue(httpServletResponse.getWriter(), msg);

    }

}
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
class ErrMsg{
    private Integer status;
    private String  msg;
}
