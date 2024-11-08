ALTER TABLE transaction_issue ADD price_buy_mxn DECIMAL(15,4) NULL;
ALTER TABLE transaction_issue ADD price_sell_mxn DECIMAL(15,4) NULL;
ALTER TABLE transaction_issue ADD price_total_buy_mxn DECIMAL(15,4) NULL;
ALTER TABLE transaction_issue ADD price_total_sell_mxn DECIMAL(15,4) NULL;
ALTER TABLE transaction_issue ADD sell_gain_loss_total_mxn DECIMAL(15,4) NULL;