package com.example.L3Application.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

//taking it as a key--so that refreshes evrytime and is unique
//here one row haslastest token inside-locking helkp another duplicate so concurrent checks in can tcheck it
@Entity
@Getter@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "daily_token_value")
public class DailyTokenCounter{
@Id
@Column(name = "issued_date")
private LocalDate issueDate;
@Column(name = "lastToken",nullable = false)
private int lastToken;

}
