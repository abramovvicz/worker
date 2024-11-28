package com.abramovvicz;

import static com.abramovvicz.TextConst.ascii;
import static com.abramovvicz.TextConst.sentence;
import static com.abramovvicz.Worker.*;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        printCentered(ascii + "\n" + sentence + "\n", 80);
        Worker worker = new Worker();
        worker.start();
    }
}