package com.walmart.ticketmaster.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Durga on 12/15/2015.
 */
@Setter
@Getter
@Entity
@Table(name = "LEVEL")
public class Level {
    @Id
    @Column(name = "ID")
    /*@GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")*/
    private int id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "PRICE")
    private BigDecimal price;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "level")
    private List<Seat> seats;
}
