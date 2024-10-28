# 🚗 Java Racing Car Precourse

경주용 자동차 게임 프로젝트입니다. 가장 멀리 이동한 자동차를 우승자로 출력합니다. 아래는 프로젝트의 구조와 기능을 상세히 설명한 내용입니다.

---

## 📂 **프로젝트 구조**

```
java-racingcar-precourse  
│  
├── controller  
│   ├── CarSetupController.java   // 사용자 입력 처리 및 자동차 리스트 반환  
│   ├── GameController.java  // 게임 진행 관리  
│   └── MainController.java  // 메인 컨트롤러  
├── factory  
│   └── CarFactory.java      // 자동차 객체 생성 팩토리  
├── model  
│   └── Car.java             // 자동차 데이터 모델  
├── racingcar  
│   └── Application.java     // 애플리케이션 실행   
├── service  
│   └── RacingCarService.java // 게임 로직 구현  
├── util  
│   └── ParsingUtil.java     // 입력 문자열 파싱  
├── validator  
│   ├── attemptNumberValidator.java // 시도 횟수 검증  
│   └── carNameValidator.java       // 자동차 이름 검증  
└── view  
    ├── InputView.java     // 사용자 입력 뷰  
    └── OutputView.java    // 결과 출력 뷰  
```

---

# 🛠️ 구현할 기능 목록

1. **🚙 경주할 자동차 이름을 입력받는 기능**
    - `InputView`를 통해 사용자에게 쉼표(,)로 구분된 자동차 이름을 입력받습니다.
    - `ParsingUtil`을 이용해 문자열을 리스트로 변환합니다.
    - `carNameValidator`에서 이름이 비어 있거나 5자를 초과하지 않는지 검증합니다.
    - 검증된 이름 리스트를 `CarController`에서 `CarFactory`를 통해 `Car` 객체 리스트로 반환합니다.

2. **🔢 시도할 횟수를 입력받는 기능**
    - `InputView`를 통해 시도 횟수를 입력받고 `attemptNumberValidator`로 검증합니다.
    - 검증된 시도 횟수는 정수로 변환되어 반환됩니다.

3. **🚗 자동차 클래스 생성 및 전진/멈춤 기능 구현**
    - `Car` 클래스는 자동차의 이름과 현재 위치를 저장합니다.
    - `move()` 메서드는 랜덤 값에 따라 자동차를 전진시킵니다.

4. **📊 자동차 이름과 현재 위치 출력 기능**
    - `OutputView`에서 각 라운드마다 자동차의 이름과 현재 위치를 출력합니다.
    - `-` 기호를 사용해 자동차의 위치를 표시합니다.

5. **🏆 우승자 출력 기능**
    - `GameController`에서 게임 종료 후 가장 멀리 이동한 자동차를 판별합니다.
    - 여러 자동차가 동점일 경우 모두 우승자로 출력합니다.

6. **⚠️ 예외 처리 기능**
   ### **1. 자동차 이름 관련 예외 처리**

- **5글자 이하로 제한:** 자동차 이름이 5글자를 초과할 경우 예외가 발생합니다.
    - 예: `"Ferrari"` → 예외 발생
- **특수문자 미허용:** 알파벳, 숫자, 한글만 허용하며, 특수문자가 포함되면 예외가 발생합니다.
    - 예: `"Car@123"` → 예외 발생
- **공백만 입력된 경우 예외:** 이름에 공백만 입력되면 예외가 발생합니다.
    - 예: `" "` → 예외 발생
- **이름 중복 허용 안 함:** 동일한 이름은 중복될 수 없습니다. 단, 대소문자가 달라도 중복으로 인식합니다.
    - 예: `"Car"`와 `"car"`가 입력될 경우 예외 발생

### **2. 시도 횟수 관련 예외 처리**

- **숫자로 입력해야 함:** 시도 횟수가 숫자가 아니면 예외가 발생합니다.
    - 예: `"five"` → 예외 발생
- **1 이상이어야 함:** 시도 횟수가 1보다 작거나 0일 경우 예외가 발생합니다.
    - 예: `"0"`, `"-1"` → 예외 발생
- **`int` 범위 내에 있어야 함:** 시도 횟수가 `int` 범위를 초과할 경우 예외가 발생합니다.
    - 예: `"2147483648"` → 예외 발생 (int 최대값 초과)

---

# 기능 요구 사항에 명시되지 않은 내용에 대한 판단 및 구현 이유

## 1. 시도 횟수의 범위 제한 (int 사용)

- **시도 횟수는 `int` 범위로 제한**하였습니다.  
  **`long` 타입까지 허용할 필요는 없다고 판단**한 이유는, 초간단 경주 자동차 게임에서 **수십억 회의 시도**는 비현실적이기 때문입니다.
- 현실적으로 사용자가 시도할 수 있는 횟수는 `int` 범위 내에서 충분히 커버할 수 있습니다.

### **이론적인 문제**

#### 출력 한계 문제

- 예를 들어, 1억 번의 시도 동안 자동차가 모두 전진했다고 가정하면, 하이픈 1억 개를 출력해야 합니다. 이는 출력 속도와 메모리 측면에서 현실적이지 않습니다.
  그리고 자바의 System.out.println()은 콘솔 출력이기 때문에, 너무 많은 양의 문자열을 출력하면 성능 저하가 발생할 수 있습니다.
- 시도 횟수는 현실적 한계를 고려해 1,000회로 제한하였습니다.
- 1,000회가 넘어갈 경우 중간 결과를 생략합니다.

실행결과 예시->

경고: 시도 횟수가 너무 많아 중간 결과를 생략합니다.   
최종 우승자 : a

---

## 2. 자동차 이름에 특수문자, 공백 미허용

- **자동차 이름을 5글자 이하로 제한**한 상황에서 특수문자가 포함되면 **가독성이 떨어지고, 사용 편의성**에도 부정적인 영향을 미칠 수 있다고 판단했습니다.
- 이 게임에서는 **한글과 영문, 숫자를 모두 허용**하기 때문에, 특수문자와 공백이 포함되면 **사용자가 이름을 쉽게 인식하기 어렵거나 불필요한 혼란**을 초래할 수 있습니다.
- 따라서, **알파벳, 숫자, 한글만 허용**하도록 제한하여 사용자가 더 직관적으로 입력하고, 게임 진행 중에도 가독성을 높이도록 설계했습니다.

---

## 3. 자동차 이름 중복 허용 불가, 대소문자는 동일하게 판단

- **자동차 이름은 중복을 허용하지 않습니다.** 동일한 이름의 자동차가 존재하면 게임 내에서 혼란이 발생할 수 있기 때문입니다.
- **대소문자는 구별하지 않습니다.** 사용자가 이름을 입력할 때 대소문자를 구별하는 것은 **불필요한 복잡성**을 유발할 수 있다고 판단했습니다.
    - 예를 들어, `Car`와 `car`가 서로 다른 자동차로 인식되는 것은 직관적이지 않습니다.
- 따라서 **대소문자를 무시하고 동일한 이름으로 처리**하여, **사용 편의성을 높이고 혼동을 최소화**하도록 설계했습니다.

---

## 4. 입력 문자의 앞뒤 공백 제거

- **사용자가 실수로 입력한 공백을 처리하여 편의성을 높입니다.**  
  사용자는 종종 의도치 않게 입력 값의 앞이나 뒤에 공백을 추가할 수 있습니다. 이러한 공백이 포함된 입력은 예기치 못한 오류를 유발하거나, 올바른 값으로 인식되지 않을 수 있습니다.
- 쉼표(,)를 기준으로 자동차 이름을 구분하는 로직에서는 A,B,C와 A, B, C가 사실상 의미적으로 동일합니다. 따라서 이런 경우에는 trim()을 사용해 앞뒤의 불필요한 공백을 제거하는 것이 더 합리적입니다.
- 이렇게 하면 사용자 실수를 허용하면서도 의도한 대로 프로그램이 동작하도록 할 수 있습니다.

  **예:** `" Car "` → `"Car"`

---

## 5. 한 대의 자동차도 우승자로 포함 가능

- **자동차가 한 대만 있는 경우**에도 게임에서 정상적으로 진행되고, 우승자로 선정됩니다.
  참가자가 한명인 경우도 있기 떄문에, 자동차 이름을 한 개만 입력하더라도, 게임은 오류 없이 진행되며 해당 자동차가 유일한 참가자로서 우승하게 됩니다.
