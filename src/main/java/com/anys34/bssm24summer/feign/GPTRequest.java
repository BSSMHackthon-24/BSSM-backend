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
        data.add(new Messages("system", "그리고 json마지막 부분에 줄바꿈을 한 다음에 true로 되어있는 것들 영어들로 출력하라고 예시 참고 안해?"));
        data.add(new Messages("user", answer));
        this.messages = data;
    }
}
