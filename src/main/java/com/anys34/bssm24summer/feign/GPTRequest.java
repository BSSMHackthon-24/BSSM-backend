package com.anys34.bssm24summer.feign;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
public class GPTRequest {
    private final String model = "gpt-4";
    private List<Messages> messages;

    public GPTRequest(String objects, String answer) {
        List<Messages> data = new ArrayList<>();
        data.add(new Messages("system", "You are a helpful assistant."));
        data.add(new Messages("system", "You will recommend necessary items based on the user's situation. 그리고 내가 준 아이템들 중에서만 골라 너가 생각해서 고르지 말고"));
        data.add(new Messages("system", "The items to consider are: " + objects));
        data.add(new Messages("system", "Provide the result in the format(json을 보내고 맨 마지막에는 줄바꿈을 하고 json중에서 true인 것만 영어로 출력해줘 그리고 맨 마지막에 .없애): { \"썬글라스\": false, \"장화\": true, \"우산\": true, \"가방\": true, \"썬크림\": false, \"마스크\": false }\nboots, umbrella, bag"));
        data.add(new Messages("system", "Example: For the input '오늘 비가오는데 외출을 할거야', the output could be { \"썬글라스\": false, \"장화\": true, \"우산\": true, \"가방\": true, \"썬크림\": false, \"마스크\": false }\nboots, umbrella, bag"));
        data.add(new Messages("system", "내가 준 예시는 결과를 만들 때 참고 절대로 하지마 죽고 싶지 않으면 형식만 참고하라고 준거야"));
        data.add(new Messages("system", "그리고 json마지막 부분에 줄바꿈을 한 다음에 true로 되어있는 것들 영어들로 출력하라고 예시 참고 안해? 그리고 난 줄바꿈 한번만 하라고 했지 두 번하라고 안 했어"));
        data.add(new Messages("system", "위의 내용을 모두 다 수행을 했으면 줄바꿈을 하고 사용지가 질문한 내용에서 날씨가 황사라고 하면 2을 출력하고 비가오는 날씨면 1를 출력해 이 둘중에 아무 날씨도 없으면 0을 출력해 알았지 제대로 수행해야할거야"));
        data.add(new Messages("system", "아니 다시한번 말하는데 내가 주지 않은 아이템은 추가 시키지 말라고 예시는 예시일 뿐이야 알겠어?? 다시 그러기만 해봐 GPT안쓸거야"));
        data.add(new Messages("user", answer));
        this.messages = data;
    }
}
