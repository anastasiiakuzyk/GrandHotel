package ua.kpi.anastasiia.models.room;

import javax.validation.constraints.Min;
import java.util.List;

public class Room {

    int id;
    int typeId;
    String type;
    String description;

    List<Integer> options;

    String optionsList = "";

    @Min(value = 1 , message = "Price cannot be under or equal 0")
    double price;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<Integer> getOptions() {
        return options;
    }

    public void setOptions(List<Integer> options) {
        this.options = options;
    }

    public String getOptionsList() {
        if (optionsList == null) {
            optionsList = "";
        }
        return optionsList;
    }

    public void setOptionsList(String optionsList) {
        this.optionsList = optionsList;
    }
}
