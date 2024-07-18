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
        data.add(new Messages("system", "그리고 맨 앞에 weather부분에 사용지가 질문한 내용에서 날씨가 황사라고 하면 2을 적고 비가오는 날씨면 1를 적어 이 둘중에 아무 날씨도 없으면 0을 적어 알았지 제대로 수행해야할거야"));
        data.add(new Messages("system", "The items to consider are: " + objects));
        data.add(new Messages("system", "Provide the result in the format: { \"weather\": 0, \"sunglasses\": false, \"umbrella\": true, \"suncream\": false, \"mask\": false, \"eyemask\": false }"));
        data.add(new Messages("system", "Example: For the input '오늘 비가오는데 외출을 할거야', the output could be { \"weather\": 0, \"sunglasses\": false, \"umbrella\": true, \"suncream\": false, \"mask\": false, \"eyemask\": false }"));
        data.add(new Messages("system", "내가 준 예시는 결과를 만들 때 참고 절대로 하지마 죽고 싶지 않으면 형식만 참고하라고 준거야"));
        data.add(new Messages("system", "내용에서 날씨가 황사라고 하면 weather에 2을 출력하고 비가오는 날씨면 weather에 1를 출력해 이 둘중에 아무 날씨도 없으면 weather에 0을 출력해 알았지 제대로 수행해야할거야"));
        data.add(new Messages("system", "아니 다시한번 말하는데 내가 주지 않은 아이템은 추가 시키지 말라고 예시는 예시일 뿐이야 알겠어?? 다시 그러기만 해봐 GPT안쓸거야"));
        data.add(new Messages("system", "내가 주지 않은 아이템은 추가 시키지 말라고!!!!!!!!!!!!!!!!!!!!"));
        data.add(new Messages("system", "황사는 일때는 썬글라스가 굳이 필요 없을 것 같아"));
        data.add(new Messages("system", "내가 위에서 말한거 차근차근 설명해 알겠지?"));
        data.add(new Messages("user", answer));
        this.messages = data;
    }
}
