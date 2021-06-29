package br.com.produto.bo;

import java.util.ArrayList;
import java.util.Comparator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import br.com.produto.domain.Produto;
import br.com.produto.dto.ProdutoDto;
import br.com.produto.exception.BDException;
import br.com.produto.repository.ProdutoRepository;


@Component("produtoBO")
@Configuration
@ComponentScan("br.com.produto")

public class ProdutoBO implements IProdutoBO {
	
	@Autowired
	ProdutoRepository produtoRepository;
	
	private ArrayList<ProdutoDto> produtoCarrinho = new ArrayList<ProdutoDto>();
	
	public ArrayList<ProdutoDto> adicionarProdutoCarrinho(ProdutoDto produtoDto) throws BDException {
		
		try {
			
			if(null != produtoDto) {
				if(!this.produtoCarrinho.contains(produtoDto)) {
					Double valorFrete = 10.00;
					
					Double valorSomaProdutos = 0.00;
					
					for (ProdutoDto produtoDtoDoCarrinho : produtoCarrinho) {
						valorSomaProdutos = valorSomaProdutos + produtoDtoDoCarrinho.getValor();
					}
					
					if (valorSomaProdutos >= 250.00) {
						produtoDto.setValorFrete(0.00);
					} else {
						produtoDto.setValor(valorFrete);

					}
					
					this.produtoCarrinho.add(produtoDto);
					
				}else {
					System.out.println("Este produto já existe no carrino!!!");
				}
			}
		} catch (Exception e) {
			throw new BDException(e.getMessage());
		}
		return produtoCarrinho;
	}
	
	public ArrayList<ProdutoDto> removerProdutoCarrinho(ProdutoDto produtoDto) throws BDException {
		try {

			if(null != produtoDto) {
				if(!this.produtoCarrinho.contains(produtoDto)) {
					this.produtoCarrinho.add(produtoDto);
				}else {
					System.out.println("Este produto não pode ser removido, pois não existe no carrino!!!");
				}
			}
		} catch (Exception e) {
			throw new BDException(e.getMessage());
		}
		return produtoCarrinho;
		
	}

	@Override
	public ProdutoDto inserirProduto(ProdutoDto produtoDto) throws BDException {
		
		Produto produto = new Produto();
		Produto produtoRetorno = new Produto();
		
		try {
			produto.setDescricao(produtoDto.getDescricao());
			produto.setNomeProduto(produtoDto.getNomeProduto());
			produto.setValor(produtoDto.getValor());
			produto.setValorFrete(produtoDto.getValorFrete());
			
			
			produtoRetorno = produtoRepository.save(produto);
			
			produtoDto.setDescricao(produtoRetorno.getDescricao());
			produtoDto.setIdProduto(produtoRetorno.getIdProduto());
			produtoDto.setNomeProduto(produtoRetorno.getNomeProduto());
			produtoDto.setValor(produtoRetorno.getValor());
			produtoDto.setValorFrete(produtoRetorno.getValorFrete());
			
			
		} catch (Exception e) {
			throw new BDException(e.getMessage());
		}		
			
		return produtoDto;
	}

	@Override
	public ProdutoDto deletarProdutos(ProdutoDto produtoDto) throws BDException {
		
		Produto produto = new Produto();
		
		try {
			produto.setIdProduto(produtoDto.getIdProduto());
			/*produto.setDescricao(produtoDto.getDescricao());
			produto.setNomeProduto(produtoDto.getNomeProduto());
			produto.setValor(produtoDto.getValor());
			produto.setValorFrete(produtoDto.getValorFrete()); */
			
			produtoRepository.deleteById(produto.getIdProduto());
			
		} catch (Exception e) {
			throw new BDException(e.getMessage());
		}
		return produtoDto;
	}

	@Override
	public ArrayList<ProdutoDto> buscaTodosProdutos() throws BDException {
		
		
		ArrayList<ProdutoDto> arrayProdutoRetorno = new ArrayList<ProdutoDto>();
		
		try {
			ProdutoDto produtoDto = null;
			
			for (Produto produto : produtoRepository.findAll()) {
				
				produtoDto = new ProdutoDto();
				produtoDto.setDescricao(produto.getDescricao());
				produtoDto.setIdProduto(produto.getIdProduto());
				produtoDto.setNomeProduto(produto.getNomeProduto());
				produtoDto.setValor(produto.getValor());
				produtoDto.setValorFrete(produto.getValorFrete());
				
				arrayProdutoRetorno.add(produtoDto);
			}
			
		} catch (Exception e) {
			throw new BDException(e.getMessage());
		}
		
		return arrayProdutoRetorno;
	}

	@Override
	public ArrayList<ProdutoDto> ordernarProdutoPorPreco(ArrayList<ProdutoDto> listaProdutos) throws BDException {
		
		try {
			listaProdutos.sort((Comparator<? super ProdutoDto>) listaProdutos.get(2));
			
		} catch (Exception e) {
			throw new BDException(e.getMessage());
		}
		return listaProdutos;
	}

	@Override
	public ProdutoDto atualizarProduto(ProdutoDto produtoDto) throws BDException {
	Produto produto = new Produto();
		
		try {
			produto.setDescricao(produtoDto.getDescricao());
			produto.setNomeProduto(produtoDto.getNomeProduto());
			produto.setValor(produtoDto.getValor());
			produto.setValorFrete(produtoDto.getValorFrete());
			produto.setIdProduto(produtoDto.getIdProduto());
						
			produtoRepository.save(produto);
			 	
		} catch (Exception e) {
			throw new BDException(e.getMessage());
		}		
			
		return produtoDto;
	}


	
}
