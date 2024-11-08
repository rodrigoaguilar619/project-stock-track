ALTER TABLE transaction_issue CHANGE price_buy_mxn price_buy_mxn DECIMAL(15,4) NULL AFTER price_buy;
ALTER TABLE transaction_issue CHANGE price_sell_mxn price_sell_mxn DECIMAL(15,4) NULL AFTER price_sell;
ALTER TABLE transaction_issue CHANGE price_total_buy_mxn price_total_buy_mxn DECIMAL(15,4) NULL AFTER price_total_buy;
ALTER TABLE transaction_issue CHANGE price_total_sell_mxn price_total_sell_mxn DECIMAL(15,4) NULL AFTER price_total_sell;
ALTER TABLE transaction_issue CHANGE sell_gain_loss_total_mxn sell_gain_loss_total_mxn DECIMAL(15,4) NULL AFTER sell_gain_loss_total;