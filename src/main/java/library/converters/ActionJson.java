package library.converters;

import lombok.Builder;

import lombok.Getter;

@Getter
@Builder
public class ActionJson {
    private Integer bookId;
    private Integer userId;
}
