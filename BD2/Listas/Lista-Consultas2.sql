--Funções de agregação:
--Encontre a soma total de depósitos para cada cliente
select d.nome_cliente, sum(d.saldo_deposito) as total_depositos
	from deposito as d
	group by d.nome_cliente; -- ok

--Encontre a soma total de depósitos para cada cliente 
--e ordene pela ordem descendente dos depósitos
select d.nome_cliente, sum(d.saldo_deposito) 
	from deposito as d
	group by d.nome_cliente
	order by sum(d.saldo_deposito) desc; -- ok

--Conte quantos depósitos realizou cada cliente
select nome_cliente, count(*) 
	from deposito
	group by nome_cliente; --ok

--Conte quantos depósitos realizou cada cliente e ordene 
--pelo ordem descendente da quantidade e depositos
select nome_cliente, count(*) 
	from deposito
		group by nome_cliente
		order by count(1) desc; -- ok

--Encontre a soma total de depósitos para cada cliente,
--e também a quantidade de depósitos. Mostre o resultado
--ordenado primeiro pela ordem ascendente da quantidade de depósitos
--e depois pela soma total descendente de depósitos de cada cliente
select nome_cliente, sum(saldo_deposito), count(1) 
	from deposito
	group by nome_cliente 
	order by count(1), sum(saldo_deposito) desc; -- ok

--Encontre o número de depositantes em cada agência
select d.nome_agencia, count(d.nome_cliente)
	from deposito as d
	group by d.nome_agencia; -- ok

--Mas a cláusula anterior está errada porque um cliente
--pode fazer mais de um depósito por agencia. Veja:
select d.nome_agencia, d.nome_cliente, count(d.nome_cliente)
	from deposito as d
	group by d.nome_agencia , d.nome_cliente; -- ok

--Solução:
select d.nome_agencia, count(d.nome_cliente)
	from deposito as d
	group by d.nome_agencia; -- ok

--Encontre o saldo médio de depósitos de cada agência
select d.nome_agencia, avg(d.saldo_deposito)
	from deposito as d  
	group by d.nome_agencia; -- ok

--Encontre o saldo médio de depósitos de cada agência
--mas mostre apenas as agencias e saldo médio que forem
--maiores do que R$ 1.200,00
select nome_agencia, avg(saldo_deposito)
	from deposito
	group by nome_agencia
	having avg(saldo_deposito) > 1200; -- ok

--Selecione o valor do maior depósito
select max(d.saldo_deposito) from deposito as d; -- ok

--Selecione o valor do maior, da média e do menor depósito
select max(d.saldo_deposito), avg(d.saldo_deposito), min(d.saldo_deposito) from deposito as d; -- ok

--Selecione o valor do maior, da média 
--e do menor depósito, todos por agencia
select d.nome_agencia, max(d.saldo_deposito), avg(d.saldo_deposito), min(d.saldo_deposito)
	from deposito as d
	group by d.nome_agencia; -- ok

--Selecione o nome do cliente que fez o maior deposito
select nome_cliente, saldo_deposito
	from deposito
	where saldo_deposito = (select max(saldo_deposito) from deposito); -- ok
	