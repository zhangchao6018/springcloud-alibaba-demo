package com.itmuch.contentcenter.controller;

import com.itmuch.contentcenter.domain.dto.ShareAuditDto;
import com.itmuch.contentcenter.domain.entity.Share;
import com.itmuch.contentcenter.service.ShareService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description:
 * @Author: zhangchao
 * @Date: 2020-01-05 22:27
 **/
@RestController
@RequestMapping("/admin/shares")
@RequiredArgsConstructor(onConstructor =  @__(@Autowired))
public class ShareAdminController {

    private final ShareService shareService;

    /**
     * 分布式事务-rocketmq原生api实现/stream编程模型 需要去shareService手动切换
     * @param id
     * @param auidtDto
     * @return
     */
    @PostMapping("/audit/{id}")
    public Share auditbyId(@PathVariable Integer id , @RequestBody ShareAuditDto auidtDto){
        //todo 认证授权
        return this.shareService.auditbyId(id,auidtDto);
    }

}
