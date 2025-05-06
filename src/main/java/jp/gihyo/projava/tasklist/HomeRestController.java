package jp.gihyo.projava.tasklist;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

//コントローラだと認識
@RestController
public class HomeRestController {

    record TaskItem(String id, String task, String deadline, boolean done) {}
    private List<TaskItem> taskItems = new ArrayList<>();
    //クライアントからのリクエスト処理をするメソッドだとマーク
    @RequestMapping(value="/resthello")
    String hello() {
        return """
                Hello.
                It Works!
                現在時刻は%sです。
                """.formatted(LocalDateTime.now());
    }

    //クライアントからのリクエストを処理。GETメソッド専用.
    @GetMapping("/restadd")
    String addItem(@RequestParam("task") String task, //httpリクエストのパラメータと関連づけられる.
                   @RequestParam("deadline") String deadline) {
        String id = UUID.randomUUID().toString().substring(0, 8); //ランダムにIDを生成.
        TaskItem item = new TaskItem(id, task, deadline, false);
        taskItems.add(item);

        return "タスクを追加しました。";
    }

    @GetMapping("/restlist")
    String listItems() {
        String result = taskItems.stream()
                .map(TaskItem::toString)
                .collect(Collectors.joining(", "));
        return result;
    }
}
