package com.services.tct.Parser;

import java.io.Serializable;

public class RSSItem implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long _status = null;
	private Long _payId = null;
	private Long _billId = null;
	private Long _amount = null;
	private String _message = null;
	private Long _telNo = null;
	private boolean _isSelected = false;
	private boolean _isPayed = false;


	public void setStatus(Long status) {
		_status = status;
	}

	public void setIsPayed(boolean isPayed) {

		_isPayed = isPayed;
	}

	public void setIsSelected(boolean isSelected) {

		_isSelected = isSelected;
	}

	public void setPayId(Long payId) {
		_payId = payId;
	}

	public void setBillId(Long billId) {
		_billId = billId;
	}

	public void setAmount(Long amount) {
		_amount = amount;
	}

	public void setMessage(String message) {
		_message = message;
	}

	public void setTelNo(Long telNo) {
		_telNo = telNo;
	}

	public Long getTelNo() {
		return _telNo;
	}
	
	public String getMessage() {
		return _message;
	}
	
	public Long getAmount() {
		return _amount;
	}

	public boolean getIsSelected() {

		return _isSelected;
	}

	public boolean getIsPayed() {

		return _isPayed;
	}

	public Long getBillId() {
		return _billId;
	}

	public Long getPayId() {
		return _payId;
	}

	public Long getStatus() {
		return _status;
	}

}
