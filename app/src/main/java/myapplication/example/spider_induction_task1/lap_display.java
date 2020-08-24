package myapplication.example.spider_induction_task1;

public class lap_display {
    private String lap_time;
    private Integer lap_number;

    public lap_display(String time, Integer number){
        lap_time = time;
        lap_number = number;
    }

    public Integer getLap_number(){
        return lap_number;
    }

    public String getLap_time(){
        return lap_time;
    }
}
