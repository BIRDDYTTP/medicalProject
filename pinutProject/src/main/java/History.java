public class History {
    private String p_id;
    private String h_id;
    private String detail;
    private String date;

    public History(String p_id, String h_id, String detail, String date) {
        this.p_id = p_id;
        this.h_id = h_id;
        this.detail = detail;
        this.date = date;
    }

    public String getP_id() {
        return p_id;
    }

    public String getH_id() {
        return h_id;
    }

    public String getDetail() {
        return detail;
    }

    public String getDate() {
        return date;
    }
}
