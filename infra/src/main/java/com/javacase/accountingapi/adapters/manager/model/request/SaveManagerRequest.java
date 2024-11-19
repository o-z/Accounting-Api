package com.javacase.accountingapi.adapters.manager.model.request;


import com.javacase.accountingapi.manager.usecase.SaveManager;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaveManagerRequest implements Serializable {

  @NotNull
  private String firstName;

  @NotNull
  private String lastName;

  @NotNull
  @Pattern(regexp = "^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "Email must be correct format.")
  private String email;

  public SaveManager toModel() {
    return SaveManager.builder()
        .firstName(firstName)
        .lastName(lastName)
        .email(email)
        .build();
  }
}
