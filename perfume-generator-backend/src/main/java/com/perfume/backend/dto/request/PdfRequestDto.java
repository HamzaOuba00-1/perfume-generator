package com.perfume.backend.dto.request;

import java.util.List;

public class PdfRequestDto {

    private List<OilLine> oils;
    private int volume;

    public List<OilLine> getOils() {
        return oils;
    }

    public void setOils(List<OilLine> oils) {
        this.oils = oils;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public static class OilLine {
        private String name;
        private double percent;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getPercent() {
            return percent;
        }

        public void setPercent(double percent) {
            this.percent = percent;
        }
    }
}
