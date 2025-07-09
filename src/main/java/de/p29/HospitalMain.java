package de.p29;

import de.p29.tui.TUIStarter;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;

@QuarkusMain
public class HospitalMain {
    public static void main(String[] args) {
        Quarkus.run(TUIStarter.class, args);
    }
}
