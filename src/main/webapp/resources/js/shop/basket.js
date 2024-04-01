$(document).ready(function() {
	$(".quantityBtn").on("click", modifyQuantity);
	$(".removeBtn").on("click", removeBasket);
	$("#checkAll").on("click", checkAll);
	$(".productCheckBox").on("click", checkProduct);
	$("#purchaseBtn").on("click", purchaseChecked);
});

function purchaseChecked() {
	var purchaseBaskets = [];
	var hasChecked = false;
	$(".productCheckBox").each(function() {
		if ($(this).is(":checked")) {
			hasChecked = true;
			const productId = $(this).data("productId");
			$(`.selectedOption[data-product-id="${productId}"]`).each(function() {
				const basketId = $(this).data("basketId");
				purchaseBaskets.push(basketId);
			});
		}
	});
	
	if (!hasChecked) {
		alert("선택한 상품이 없습니다");
	} else {
		$.ajax({
			type: "POST",
			url: "basket/makeOrder",
			data: JSON.stringify(purchaseBaskets),
			success: function(data) {
				var url = "order?orderId=" + data;
				location.href = url;
			}
		});
	}
}

function checkAll() {
	if ($(this).is(":checked")) {
		$(".productCheckBox").prop("checked", true);
	} else {
		$(".productCheckBox").prop("checked", false);
	}
	
	updateBasketTotalPrice();
}

function checkProduct() {
	if (!$(this).is(":checked")) {
		$("#checkAll").prop("checked", false);
	} else {
		updateCheck();
	}
	
	updateBasketTotalPrice();
}

function updateCheck() {
	var hasNotChecked = false;
	$(".productCheckBox").each(function() {
		if (!$(this).is(":checked")) {
			hasNotChecked = true;
		}
	});
	
	if (!hasNotChecked) {
		$("#checkAll").prop("checked", true);
	}
}

function modifyQuantity() {
	const optionId = $(this).data("optionId");
	const action = $(this).data("action");
	
	const option = $(`.selectedOption[data-option-id="${optionId}"]`).first();
	const productId = option.data("productId");
	
	var quantity = option.data("quantity");
	
	if (action === "sub" && quantity === 1) {
		alert("1개 이상 가능");
		return;
	}
	
	$.ajax({
		type: "post",
		async: false,
		url: "basket/modifyQuantity",
		data: {
			"optionId" : optionId,
			"action" : action
		},
		success: function(result) {
			quantity = result;
		}
	})
	
	// 수량, 가격 변경
	option.data("quantity", quantity);
	
	const quantityText = $(`.optionQuantityText[data-option-id="${optionId}"]`).first();
	quantityText.text(quantity);
	
	const optionPrice = option.data("optionPrice");
	option.data("optionTotalPrice", optionPrice * quantity);
	
	const optionDPrice = option.data("optionDPrice");
	option.data("optionTotalDPrice", optionDPrice * quantity);
	
	const priceText = $(`.optionPriceText[data-option-id="${optionId}"]`).first();
	if (optionDPrice != 0) {
		priceText.text((optionDPrice * quantity).toLocaleString());
	} else {
		priceText.text((optionPrice * quantity).toLocaleString());		
	}
	
	updateProductTotalPrice(productId);
	updateBasketTotalPrice();
}

function removeBasket() {
	const btnOf = $(this).data("btnOf");
	
	var targetId;
	switch (btnOf) {
	case "product":
		targetId = $(this).data("productId");
		break;
	case "option":
		targetId = $(this).data("optionId");
		break;
	}
	
	$.ajax({
		type: "post",
		async: true,
		url: "basket/removeBasket",
		data: {
			"target" : btnOf,
			"targetId" : targetId
		},
		success: function() {
			
		}
	});
	
	switch (btnOf) {
	case "product":
		$(`.productItem[data-product-id="${targetId}"]`).first().remove();
		updateBasketTotalPrice();
		break;
	case "option":
		const productId = $(`.selectedOption[data-option-id="${targetId}"]`).first().
			data("productId");
		$(`.selectedOption[data-option-id="${targetId}"]`).first().remove();
		updateProductTotalPrice(productId);
		const priceText = $(`.productTotalPrice[data-product-id="${productId}"]`).first();
		if (priceText.data("productTotalPrice") === 0) {
			$(`.productItem[data-product-id="${productId}"]`).first().remove();
		}
		updateBasketTotalPrice();
		break;
	}
	
	updateCheck();
}

function updateProductTotalPrice(productId) {
	var productTotalPrice = 0;
	var productTotalDPrice = 0;
	$(`.selectedOption[data-product-id="${productId}"]`).each(function() {
		productTotalPrice += $(this).data("optionTotalPrice");
		productTotalDPrice += $(this).data("optionTotalDPrice");
	});
	
	const priceText = $(`.productTotalPrice[data-product-id="${productId}"]`).first();
	if (productTotalDPrice != 0) {
		priceText.text(productTotalDPrice.toLocaleString());
	} else {
		priceText.text(productTotalPrice.toLocaleString());
	}
	
	priceText.data("productTotalPrice", productTotalPrice);
	priceText.data("productTotalDPrice", productTotalDPrice);
}

function updateBasketTotalPrice() {
	var totalPrice = 0;
	var totalDPrice = 0;
	
	$(`.productTotalPrice`).each(function() {
		const productId = $(this).data("productId");
		const checkbox = $(`.productCheckBox[data-product-id="${productId}"]`).first();
		if ($(checkbox).is(":checked")) {
			totalPrice += $(this).data("productTotalPrice");
			totalDPrice += $(this).data("productTotalDPrice");
		}
	});
	$("#totalSelectedBasketsPrice").text(totalPrice.toLocaleString());
	if (totalDPrice != 0) {
		$("#totalSelectedBasketsSubDPrice").text((totalPrice - totalDPrice).toLocaleString());		
	} else {
		$("#totalSelectedBasketsSubDPrice").text((0).toLocaleString());
	}
	
	
	var totalDeliveryFee = 0;
	
	$(`.deliveryFee`).each(function() {
		const productId = $(this).data("productId");
		const checkbox = $(`.productCheckBox[data-product-id="${productId}"]`).first();
		if ($(checkbox).is(":checked")) {
			totalDeliveryFee += $(this).data("deliveryFee");
		}
	});
	$("#totalDeliveryFee").text(totalDeliveryFee.toLocaleString());
	
	if (totalDPrice != 0) {
		$("#totalPurchasePrice").text((totalDPrice + totalDeliveryFee).toLocaleString());		
	} else {
		$("#totalPurchasePrice").text((totalPrice  + totalDeliveryFee).toLocaleString());
	}
}