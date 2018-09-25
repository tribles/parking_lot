package com.assigment.GOJEK;

import com.assigment.GOJEK.parklot.exception.NOFreeSlotException;
import com.assigment.GOJEK.parklot.exception.NoSlotFoundForRegNum;

public class ParkingLotImpl extends ParkingLot {
	
	public ParkingLotImpl() {
		super();
	}
	
	public ParkingLotImpl(int number) {
		super(number);
	}

	@Override
	public Slot getNearestAvailableSlot() throws NOFreeSlotException{
		Slot slot = this.getSlots().poll();
		
		if(slot==null)
			throw new NOFreeSlotException("No Free slot available to park your car");
		return slot;
	}

	@Override
	public Ticket park(Car car) throws NOFreeSlotException {
		Slot slot = getNearestAvailableSlot();
		
		if(slot != null) {
		  this.getRegNumMap().put(car.getRegNum(), slot);
		  this.getSlotCarMap().put(slot, car);
		}
		
		return generateTicket(car);
	}

	private Ticket generateTicket(Car car) {
		//TODO 
		return new Ticket();
	}

	@Override
	public void leave(Slot slot) throws Exception {
		//TODO.. free up car from parked data strcutures
		this.getSlots().add(slot);
		
		Car car = getSlotCarMap().get(slot);
		if(car == null)
			throw new Exception("No car found"); // need to add test coverage for this , violating TDD
		
		
		//remove slot from regNumSlotMap and SlotCarMap
		if(getRegNumMap() != null && getRegNumMap().size() > 0) {
		   getRegNumMap().remove(car.getRegNum());
		}
		
		getSlotCarMap().remove(slot); //need to add test coverage for this
		
	}

	@Override
	public Slot getSlotForRegNum(String RegNum) throws NoSlotFoundForRegNum {
		Slot slot = this.getRegNumMap().get(RegNum);
		
		if(slot == null)
			throw new NoSlotFoundForRegNum("No Slot found for this register number:"+RegNum);
		return slot;
	}
}
