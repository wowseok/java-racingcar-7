package controller;

import camp.nextstep.edu.missionutils.Randoms;
import java.util.List;
import model.Car;
import view.OutputHandler;

public class RacingCarService {

    public void gameStart(List<Car> cars, int attempt) {
        for (int i = 0; i < attempt; i++) {
            for (int j = 0; j < cars.size(); j++) {
                cars.get(j).move(Randoms.pickNumberInRange(0, 9));
                OutputHandler.printCurrentStatus(cars.get(j));
            }
            System.out.println();
        }
    }
}