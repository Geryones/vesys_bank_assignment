/*
 * Copyright (c) 2019 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved. 
 */

package bank.local;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import bank.InactiveException;
import bank.OverdrawException;

public class Driver implements bank.BankDriver {
	private Bank bank = null;

	@Override
	public void connect(String[] args) {
		bank = new Bank();
		System.out.println("connected...");
	}

	@Override
	public void disconnect() {
		bank = null;
		System.out.println("disconnected...");
	}

	@Override
	public Bank getBank() {
		return bank;
	}

	static class Bank implements bank.Bank {

		private final Map<String, Account> accounts = new HashMap<>();

		@Override
		public Set<String> getAccountNumbers() {
			return accounts.values().stream().filter(a -> a.isActive()).map(a -> a.getNumber()).collect(Collectors.toSet());
		}

		@Override
		public String createAccount(String owner) {
			Account temp = new Account(owner,  "10-"+accounts.size()+"-0");
			accounts.put(temp.getNumber(), temp);
			return temp.getNumber();
		}

		@Override
		public boolean closeAccount(String number) {
			if (accounts.containsKey(number)){
				if (accounts.get(number).getBalance() == 0.0){
					if (accounts.get(number).isActive()){
						accounts.get(number).active = false;
						return true;
					}

				}
			}

			return false;
		}

		@Override
		public bank.Account getAccount(String number) {
			return accounts.get(number);
		}

		@Override
		public void transfer(bank.Account from, bank.Account to, double amount)
				throws IOException, InactiveException, OverdrawException {
			from.withdraw(amount);
			try {
				to.deposit(amount);
			}catch(InactiveException e){
				from.deposit(amount);
				System.out.println("unable to transfer money, TO-Acc is inactive");
				e.printStackTrace();
			}
		}

	}

	static class Account implements bank.Account {
		private String number;
		private String owner;
		private double balance;
		private boolean active = true;

		Account(String owner, String number) {
			this.owner = owner;
			this.number = number;
			this.balance = 0;
		}


		@Override
		public double getBalance() {
			return balance;
		}

		@Override
		public String getOwner() {
			return owner;
		}

		@Override
		public String getNumber() {
			return number;
		}

		@Override
		public boolean isActive() {
			return active;
		}

		@Override
		public void deposit(double amount) throws InactiveException, IllegalArgumentException {
			if (!isActive()){
				throw new InactiveException("account is not active");
			}
			if (amount < 0){
				throw new IllegalArgumentException("no negative deposit");
			}
			balance += Math.abs(amount);
		}

		@Override
		public void withdraw(double amount) throws InactiveException, OverdrawException {
			if (!isActive()){
				throw new InactiveException("account not active");
			}
			if (balance < Math.abs(amount)){
				throw new OverdrawException("this is no Kontokorrent, you lack money");
			}
			balance -= Math.abs(amount);
		}

	}

}