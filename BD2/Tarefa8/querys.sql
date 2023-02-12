-- Tarefa 8
-- questão 1 tarefa 8

select c.nome_cliente, sum(d.saldo_deposito) as soma_deposito, sum(e.valor_emprestimo) as soma_emprestimo
	from cliente c, deposito d, emprestimo e
	where c.nome_cliente = d.nome_cliente
	and c.nome_cliente = e.nome_cliente
	group by c.nome_cliente, d.nome_agencia, d.numero_conta;

-- parte 2
select d.nome_cliente, sum(d.saldo_deposito) as soma_deposito, sum(e.valor_emprestimo) as soma_emprestimo
	from deposito as d, emprestimo as e
	where d.nome_cliente = e.nome_cliente
	group by d.nome_cliente, d.nome_agencia, d.numero_conta;
