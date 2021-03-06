package io.kanteen.dto;

import io.kanteen.persistance.entity.ContractOption;

import java.util.ArrayList;
import java.util.List;

public class ContractDto {

    private long id;
    private String title;
    private String description;
    private List<ContractOptionDto> options = new ArrayList<>();
    private boolean withOption;



    public ContractDto() {

    }

    public ContractDto(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public ContractDto(String title, String description, List<ContractOptionDto> options) {
        this.title = title;
        this.description = description;
        this.options = options;
    }

    public ContractDto(long id, String title, String description, List<ContractOptionDto> options, boolean withOption) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.options = options;
        this.withOption = withOption;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ContractOptionDto> getOptions() {
        return options;
    }

    public void setOptions(List<ContractOptionDto> options) {
        this.options = options;
    }

    public void addOption(ContractOptionDto option){
        this.options.add(option);
    }

    public boolean isWithOption() {
        return withOption;
    }

    public void setWithOption(boolean withOption) {
        this.withOption = withOption;
    }
}
