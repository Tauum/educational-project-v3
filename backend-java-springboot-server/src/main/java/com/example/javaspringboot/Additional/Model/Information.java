package com.example.javaspringboot.Additional.Model;

import java.time.LocalDateTime;
import javax.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Information
{

  public LocalDateTime creation;
  public LocalDateTime last_updated;
  public Long version = 0L;

}
