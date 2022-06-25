package net.voxfun.iris.vox.managers;

import java.util.Random;

public class RandomId {
    private int length = 8;
    public RandomId(int IDLength) {
        length = IDLength;
    }
    public String generate() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) { sb.append("ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890".toLowerCase().charAt(new Random().nextInt("ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890".toLowerCase().length()))); }
        return sb.toString();
    }
}
