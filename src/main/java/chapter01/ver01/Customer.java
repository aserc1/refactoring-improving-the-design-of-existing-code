package chapter01.ver01;

import java.util.Enumeration;
import java.util.Vector;

public class Customer {
	private String _name; // 姓名
	private Vector _rentals = new Vector(); // 租借记

	public Customer(String name) {
		_name = name;
	};

	public void addRental(Rental arg) {
		_rentals.addElement(arg);
	}

	public String getName() {
		return _name;
	}

	public String statement() {
		double totalAmount = 0;
		int frequentRenterPoints = 0;
		Enumeration rentals = _rentals.elements();
		String result = "Rental Record for " + getName() + "\n";
		while (rentals.hasMoreElements()) {
			Rental each = (Rental) rentals.nextElement(); // 取得一笔租借记。
			// determine amounts for each line
			double thisAmount = amountFor(each);
			// add frequent renter points （累计常客积点。
			frequentRenterPoints++;
			// add bonus for a two day new release rental
			if ((each.getMovie().getPriceCode() == Movie.NEW_RELEASE)
					&& each.getDaysRented() > 1)
				frequentRenterPoints++;
			// show figures for this rental（显示此笔租借记录）
			result += "\t" + each.getMovie().getTitle() + "\t"
					+ String.valueOf(thisAmount) + "\n";
			totalAmount += thisAmount;
		}
		// add footer lines（结尾打印）
		result += "Amount owed is " + String.valueOf(totalAmount) + "\n";
		result += "You earned " + String.valueOf(frequentRenterPoints)
				+ " frequent renter points";
		return result;
	}

	private double amountFor(Rental aRental) {
		double result =0 ;
		switch (aRental.getMovie().getPriceCode()) { // 取得影片出租价格
		case Movie.REGULAR: // 普通片
			result += 2;
			if (aRental.getDaysRented() > 2)
				result += (aRental.getDaysRented() - 2) * 1.5;
			break;
		case Movie.NEW_RELEASE: // 新片
			result += aRental.getDaysRented() * 3;
			break;
		case Movie.CHILDRENS: // 儿童。
			result += 1.5;
			if (aRental.getDaysRented() > 3)
				result += (aRental.getDaysRented() - 3) * 1.5;
			break;
		}
		return result;
	}
}
