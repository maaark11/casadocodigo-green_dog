package com.marco.casadocodigo.load;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.marco.casadocodigo.domain.Item;
import com.marco.casadocodigo.domain.Order;
import com.marco.casadocodigo.domain.User;
import com.marco.casadocodigo.repository.UserRepository;

@Component
public class RepositoryTest implements ApplicationRunner {

	private static final long ID_CLIENT_MARCO = 11l;
	private static final long ID_CLIENT_EIJI = 22l;

	private static final long ID_ITEM1 = 100l;
	private static final long ID_ITEM2 = 101l;
	private static final long ID_ITEM3 = 102l;

	private static final long ID_ORDER1 = 1000l;
	private static final long ID_ORDER2 = 1001l;

	@Autowired
	private UserRepository clientRepository;

	@Override
	public void run(ApplicationArguments applicationArguments) throws Exception {

		System.out.println(">>> INITIALIZING DATA LOAD");
		System.out.println(">>>WELCOME TO THE JUNGLE<<<");
		
		System.out.println("1/7 - CREATING CLIENTS");
		User marco = new User(ID_CLIENT_MARCO, "Marco Aurelio", "Rua Bom Pastor, 200");
		User eiji = new User(ID_CLIENT_EIJI, "Diego Eiji", "Rua Dos Japas, 890");

		System.out.println("2/7 - CREATING ITENS");
		Item item1 = new Item(ID_ITEM1, "Dog Tradicional", 25.0);
		Item item2 = new Item(ID_ITEM2, "Dog Picante", 29.0);
		Item item3 = new Item(ID_ITEM3, "Dog Salada", 22.0);

		System.out.println("3/7 - CREATING ORDER LIST");
		List<Item> orderList1 = new ArrayList<Item>();
		List<Item> orderList2 = new ArrayList<Item>();
		
		System.out.println("4/7 - ADDING ITENS TO ORDERLIST");
		orderList1.add(item1);
		orderList1.add(item3);
		orderList2.add(item2);
		orderList2.add(item2);

		Order order1 = new Order(ID_ORDER1, marco, orderList1);
		Order order2 = new Order(ID_ORDER2, eiji, orderList2);
		System.out.println("5/7 - CREATING ORDERS");
		
		System.out.println("6/7 - ADDING ORDERS TO CLIENTS");
		marco.newOrder(order1);
		eiji.newOrder(order2);
		
		clientRepository.saveAndFlush(marco);
		clientRepository.saveAndFlush(eiji);
		System.out.println("7/7 - PERSISTING TO DATABASE");
		
		
	}

}
