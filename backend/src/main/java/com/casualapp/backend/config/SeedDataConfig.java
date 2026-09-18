package com.casualapp.backend.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app.seed")
public class SeedDataConfig {

    private String language = "en";

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public static class JobData {
        public String title;
        public String location;
        public String description;
        public int monthsAgo;
        public int dayOfMonth;
        public int startHour;
        public int startMinute;
        public int durationHours;
        public String hourlyRate;
        public int totalSlots;
        public int coordinatorIndex;

        public JobData(String title, String location, String description, int monthsAgo, int dayOfMonth,
                      int startHour, int startMinute, int durationHours, String hourlyRate,
                      int totalSlots, int coordinatorIndex) {
            this.title = title;
            this.location = location;
            this.description = description;
            this.monthsAgo = monthsAgo;
            this.dayOfMonth = dayOfMonth;
            this.startHour = startHour;
            this.startMinute = startMinute;
            this.durationHours = durationHours;
            this.hourlyRate = hourlyRate;
            this.totalSlots = totalSlots;
            this.coordinatorIndex = coordinatorIndex;
        }
    }

    public java.util.List<JobData> getHistoricalJobs() {
        if ("zh".equalsIgnoreCase(language)) {
            return getChineseHistoricalJobs();
        }
        return getEnglishHistoricalJobs();
    }

    public java.util.List<JobData> getUpcomingJobs() {
        if ("zh".equalsIgnoreCase(language)) {
            return getChineseUpcomingJobs();
        }
        return getEnglishUpcomingJobs();
    }

    // ========== ENGLISH JOBS ==========

    private java.util.List<JobData> getEnglishHistoricalJobs() {
        return java.util.List.of(
                new JobData("Wedding Banquet Server", "Harbour View Hotel", "Wedding banquet dinner service.", 4, 5, 17, 0, 6, "120.00", 6, 0),
                new JobData("Breakfast Buffet Server", "Central Hotel", "Breakfast buffet service and table reset.", 4, 11, 7, 0, 5, "105.00", 5, 1),
                new JobData("Conference Setup Crew", "Metropark Hotel", "Conference room setup and guest support.", 4, 18, 9, 0, 7, "110.00", 7, 0),
                new JobData("Cocktail Bartender", "Grand Harbour Hotel", "Evening cocktail reception service.", 4, 24, 18, 0, 5, "140.00", 5, 1),
                new JobData("Restaurant Server", "Kowloon City Hotel", "Dinner restaurant floor service.", 3, 4, 17, 30, 6, "115.00", 6, 0),
                new JobData("Kitchen Helper", "Harbour View Hotel", "Food preparation and kitchen support.", 3, 10, 15, 0, 7, "100.00", 7, 1),
                new JobData("Housekeeping Support", "Central Hotel", "Evening room turnover support.", 3, 17, 18, 0, 5, "95.00", 5, 0),
                new JobData("Event Usher", "Grand Harbour Hotel", "Corporate event guest greeting and seating.", 3, 22, 13, 0, 8, "105.00", 8, 0)
        );
    }

    private java.util.List<JobData> getEnglishUpcomingJobs() {
        return java.util.List.of(
                new JobData("Wedding Banquet Server", "Harbour View Hotel", "Wedding banquet dinner service.", 0, 20, 17, 0, 6, "120.00", 6, 0),
                new JobData("Breakfast Buffet Server", "Central Hotel", "Breakfast buffet service and table reset.", 0, 21, 7, 0, 5, "105.00", 5, 0),
                new JobData("Conference Setup Crew", "Metropark Hotel", "Conference room setup and guest support.", 0, 22, 9, 0, 7, "110.00", 7, 0),
                new JobData("Restaurant Server", "Kowloon City Hotel", "Dinner restaurant floor service.", 0, 23, 17, 30, 6, "115.00", 6, 0),
                new JobData("Kitchen Helper", "Harbour View Hotel", "Food preparation and kitchen support.", 0, 24, 15, 0, 7, "100.00", 7, 0),
                new JobData("Cocktail Bartender", "Grand Harbour Hotel", "Evening cocktail reception service.", 0, 25, 18, 0, 5, "140.00", 5, 1)
        );
    }

    // ========== CHINESE JOBS ==========

    private java.util.List<JobData> getChineseHistoricalJobs() {
        return java.util.List.of(
                new JobData("婚宴服務員", "海港景觀酒店", "婚宴晚餐服務。", 4, 5, 17, 0, 6, "120.00", 6, 0),
                new JobData("早餐自助餐服務員", "中央酒店", "早餐自助餐服務和餐桌重置。", 4, 11, 7, 0, 5, "105.00", 5, 1),
                new JobData("會議設置團隊", "美都酒店", "會議室設置和客人支持。", 4, 18, 9, 0, 7, "110.00", 7, 0),
                new JobData("雞尾酒調酒師", "大灣景觀酒店", "晚間雞尾酒招待會服務。", 4, 24, 18, 0, 5, "140.00", 5, 1),
                new JobData("餐廳服務員", "九龍城酒店", "晚餐餐廳樓面服務。", 3, 4, 17, 30, 6, "115.00", 6, 0),
                new JobData("廚房幫手", "海港景觀酒店", "食物準備和廚房支持。", 3, 10, 15, 0, 7, "100.00", 7, 1),
                new JobData("客房服務支持", "中央酒店", "晚間房間清潔支持。", 3, 17, 18, 0, 5, "95.00", 5, 0),
                new JobData("活動接待員", "大灣景觀酒店", "企業活動客人迎接和安排座位。", 3, 22, 13, 0, 8, "105.00", 8, 0)
        );
    }

    private java.util.List<JobData> getChineseUpcomingJobs() {
        return java.util.List.of(
                new JobData("婚宴服務員", "海港景觀酒店", "婚宴晚餐服務。", 0, 20, 17, 0, 6, "120.00", 6, 0),
                new JobData("早餐自助餐服務員", "中央酒店", "早餐自助餐服務和餐桌重置。", 0, 21, 7, 0, 5, "105.00", 5, 0),
                new JobData("會議設置團隊", "美都酒店", "會議室設置和客人支持。", 0, 22, 9, 0, 7, "110.00", 7, 0),
                new JobData("餐廳服務員", "九龍城酒店", "晚餐餐廳樓面服務。", 0, 23, 17, 30, 6, "115.00", 6, 0),
                new JobData("廚房幫手", "海港景觀酒店", "食物準備和廚房支持。", 0, 24, 15, 0, 7, "100.00", 7, 0),
                new JobData("雞尾酒調酒師", "大灣景觀酒店", "晚間雞尾酒招待會服務。", 0, 25, 18, 0, 5, "140.00", 5, 1)
        );
    }
}
