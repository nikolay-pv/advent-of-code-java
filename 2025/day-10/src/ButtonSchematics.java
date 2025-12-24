import java.util.ArrayList;
import java.util.HashMap;

public class ButtonSchematics {
    private long indicators;
    private ArrayList<Long> buttons;

    public ButtonSchematics() {
        this.buttons = new ArrayList<>();
    }

    public long getIndicatorsBits() {
        return this.indicators;
    }

    public ArrayList<Long> getButtonsBits() {
        return this.buttons;
    }

    public long countPressesForIndicator() {
        final long goal = this.indicators;
        return countPressesForIndicator(goal);
    }

    private long countPressesForIndicator(long goal) {
        HashMap<Long, Long> queue = new HashMap<>();
        // init with individual first buttons pressed
        for (int j = 0; j < this.buttons.size(); j++) {
            Long value = this.buttons.get(j);
            if (value.longValue() == goal) {
                return 1;
            }
            queue.put(Long.valueOf(1L << (j + 1)), value);
        }
        // there is no point in toggling the same button twice because it would just
        // nullify the first press, so search for all possible press sequences which
        // will result in the `goal`
        // do bfs
        for (int pressCount = 2; pressCount <= this.buttons.size(); pressCount++) {
            // pressCount is shifted by 1 to the right for ease of counting
            HashMap<Long, Long> new_intermediate = new HashMap<>();
            for (var entry : queue.entrySet()) {
                for (int j = 1; j <= this.buttons.size(); j++) {
                    // j is shifted by 1 to the right to help with bit operations
                    long pressedButtons = entry.getKey().longValue();
                    long indicators = entry.getValue().longValue();
                    if ((pressedButtons & (1L << j)) == 0) {
                        pressedButtons |= (1L << j);
                        if (new_intermediate.containsKey(pressedButtons)) {
                            continue;
                        }
                        indicators ^= this.buttons.get(j - 1).longValue();
                        if (indicators == goal) {
                            return pressCount;
                        }
                        new_intermediate.put(Long.valueOf(pressedButtons), Long.valueOf(indicators));
                    }
                }
            }
            queue = new_intermediate;
        }
        return -1;
    }

    public static ButtonSchematics parseButtonSchematics(String line) {
        var schematics = new ButtonSchematics();
        for (var input : line.trim().split("\\s")) {
            long bits = 0;
            if (input.startsWith("[")) {
                long index = 1L;
                for (int i = 1; i < input.length() - 1; i++) {
                    if (input.charAt(i) == '#') {
                        bits |= index;
                    }
                    index <<= 1;
                }
                schematics.indicators = bits;
            } else if (input.startsWith("(")) {
                // trim to avoid ()
                for (var idxStr : input.substring(1, input.length() - 1).split(",")) {
                    long idx = Long.parseLong(idxStr);
                    bits |= (1L << idx);
                }
                schematics.buttons.add(Long.valueOf(bits));
            } else {
                continue;
            }
        }
        return schematics;
    }
}