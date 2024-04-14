package net.blusalt.dispatchapi.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Olusegun Adeoye
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ModelData {

    private List models;
}
