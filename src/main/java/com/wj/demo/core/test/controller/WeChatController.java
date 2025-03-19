package com.wj.demo.core.test.controller;

import com.wj.demo.framework.common.model.html.*;
import com.wj.demo.framework.common.utils.HtmlUtils;
import com.wj.demo.framework.weChat.modal.WeChatMsgParam;
import com.wj.demo.framework.weChat.modal.msg.WeChatText;
import com.wj.demo.framework.weChat.modal.result.WeChatResult;
import com.wj.demo.framework.weChat.utils.WeChatUtils;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author W.Jian
 * @version 1.0
 * @Desc
 * @date 2025/1/10 16:44
 */
@RestController
@RequestMapping("/WeChat")
public class WeChatController {

    @Resource
    private WeChatUtils weChatUtils;

    @PostMapping("/send")
    public WeChatResult sendMessage(@RequestBody WeChatMsgParam param) {
        List<Tr> headerTrList = new ArrayList<>() {{
            add(Tr.build(new ArrayList<Th>() {{
                add(Th.build("日期", null, null, null));
                add(Th.build("02-01", null, 2, null));
                add(Th.build("02-02", null, 2, null));
            }}, Tr.DEFAULT_HEAD_STYLE));
            add(Tr.build(new ArrayList<Th>() {{
                add(Th.build("产线", null, null, null));
                add(Th.build("1线", null, null, null));
                add(Th.build("2线", null, null, null));
                add(Th.build("1线", null, null, null));
                add(Th.build("2线", null, null, null));
            }}, Tr.DEFAULT_HEAD_STYLE));
        }};
        List<Tr> bodyTrList = new ArrayList<>() {{
            add(Tr.build(new ArrayList<Th>() {{
                add(Th.build("品种1", null, null, null));
                add(Th.build(null, null, null, null));
                add(Th.build(null, null, null, null));
                add(Th.build("100", null, null, null));
                add(Th.build(null, null, null, null));
            }}, Tr.DEFAULT_BODY_STYLE));
            add(Tr.build(new ArrayList<Th>() {{
                add(Th.build("品种2", null, null, null));
                add(Th.build(null, null, null, null));
                add(Th.build("200", null, null, null));
                add(Th.build(null, null, null, null));
                add(Th.build(null, null, null, null));
            }}, Tr.DEFAULT_BODY_STYLE));
        }};
        Thead thead = Thead.build(headerTrList, null);
        Tbody tbody = Tbody.build(bodyTrList, null);
        Table table = Table.build(null, thead, tbody, Table.DEFAULT_BORDER, null);
        String content = HtmlUtils.createTable(table);
        WeChatText text = new WeChatText();
        text.setContent(content);
        param.setText(text);
        return weChatUtils.sendAppMsg(param);
    }

}
