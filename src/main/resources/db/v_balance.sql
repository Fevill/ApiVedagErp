SELECT bd.number,bd.label, bd.debit, bc.credit
FROM v_balance_debit AS bd
LEFT JOIN v_balance_credit AS bc ON bd.number = bc.number