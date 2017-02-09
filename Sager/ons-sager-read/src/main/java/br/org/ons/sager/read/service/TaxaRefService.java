package br.org.ons.sager.read.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.jms.JMSException;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.keyvalue.core.IterableConverter;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import br.org.ons.geracao.cadastro.InterligacaoInternacional;
import br.org.ons.geracao.cadastro.TipoInstalacao;
import br.org.ons.geracao.cadastro.Usina;
import br.org.ons.geracao.evento.taxa.Taxa;
import br.org.ons.platform.common.EventMessage;
import br.org.ons.platform.common.exception.OnsRuntimeException;
import br.org.ons.sager.instalacao.event.InstalacaoCadastradaEvent;
import br.org.ons.sager.read.domain.QTaxaRef;
import br.org.ons.sager.read.domain.TaxaRef;
import br.org.ons.sager.read.repository.TaxaRefRepository;
import br.org.ons.sager.read.web.rest.exceptions.BadRequestException;
import br.org.ons.sager.read.web.rest.util.JavaUtil;

/**
 * Service Implementation for managing TaxaRef.
 */
@Service
public class TaxaRefService {

    private final Logger log = LoggerFactory.getLogger(TaxaRefService.class);
    
    @Inject
    private TaxaRefRepository taxaRefRepository;
    
    private static final String COSR_S = "COSR-S";
    
    private static final String COSR_NE = "COSR-NE";
    
	private static final String COSR_SE = "COSR-SE";
	
	private static final String COSR_NCO = "COSR-NCO";
	
	private static final String TAXA_REF = "taxaRef";
	
	private QTaxaRef qTaxaRef = new QTaxaRef("taxaRef");
	
	@JmsListener(destination = "platform/EventTopic/br.org.ons.sager.instalacao.event.InstalacaoCadastradaEvent", 
			containerFactory = "JmsTopicListenerContainerFactory", selector = "isReplay = FALSE")
	public void handleInstalacaoCadastradaEvent(EventMessage<InstalacaoCadastradaEvent> eventMessage) throws JMSException {
		
		try{
		
			InstalacaoCadastradaEvent event = eventMessage.getEvent();
			
			this.saveTaxaReferencia(event);
			
		}catch(Exception e){
			log.error("Ero ao Salvar a Taxa de Referência",e);
		}
		
	}
	
	private void saveTaxaReferencia(InstalacaoCadastradaEvent event){
		
		if(event.getInstalacao() instanceof Usina){
			this.saveTaxaReferenciaByUsina(event);
		}else{
			this.saveTaxaReferenciaByInterligacao(event);
		}
	}    
	
	private void saveTaxaReferenciaByUsina(InstalacaoCadastradaEvent event){
		Usina usina = (Usina) event.getInstalacao();
		if( usina == null){
			throw new OnsRuntimeException("Usina não Encontrada ");
		}
		
		// Uma TEIF e uma IP ( sempre )
		for(Taxa taxa : JavaUtil.emptyIfNull(event.getInstalacao().getTaxasReferencia())){
			//Verifica se já existe, apenas atualiza
			QTaxaRef qTxRf = new QTaxaRef(TAXA_REF);
			TaxaRef txRef = taxaRefRepository.findOne(qTxRf.cos.eq(usina.getCosr()).
					and(qTxRf.dtRef.eq(taxa.getPeriodo().getDataInicio().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()).
					and(qTxRf.usi.eq(usina.getNomeCurto()).
					and(qTxRf.usiid.eq(usina.getId())))));
			
			//Se for a taxa não existe cria todos os valores para taxa, se ela já existe apenas adiciona a taxa que falta.
			if( txRef == null){
				txRef = new TaxaRef();
				txRef.setTipoInstalacao(TipoInstalacao.USINA.toString());
				txRef.setCos(usina.getCosr());
				txRef.setDtRef(taxa.getPeriodo().getDataInicio().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
				txRef.setUsi(usina.getNomeCurto());
				txRef.setUsiid(usina.getId());
			}
			//Atualiza somente as taxas.
			if("TEIP".contains(taxa.getCodigo().toUpperCase())){
				txRef.setIp(taxa.getValor());
			}else{
				txRef.setTeif(taxa.getValor());
			}
			taxaRefRepository.save(txRef);
		}
	}
	
	private void saveTaxaReferenciaByInterligacao(InstalacaoCadastradaEvent event){
		InterligacaoInternacional interLig = (InterligacaoInternacional) event.getInstalacao();
		if( interLig == null){
			throw new OnsRuntimeException("InterligaçãoInternacional não Encontrada ");
		}
				
		// Uma TEIF e uma IP ( sempre )
		for(Taxa taxa : JavaUtil.emptyIfNull(event.getInstalacao().getTaxasReferencia())){
			
			//Verifica se já existe, apenas atualiza
			QTaxaRef qTxRf = new QTaxaRef(TAXA_REF);
			TaxaRef txRef = taxaRefRepository.findOne(qTxRf.cos.eq(interLig.getCosr()).
					and(qTxRf.dtRef.eq(taxa.getPeriodo().getDataInicio().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()).
					and(qTxRf.usi.eq(interLig.getNomeCurto()).
					and(qTxRf.usiid.eq(interLig.getId())))));
			
			//Se for a taxa não existe cria todos os valores para taxa, se ela já existe apenas adiciona a taxa que falta.
			if( txRef == null){
				txRef = new TaxaRef();
				txRef.setTipoInstalacao(TipoInstalacao.INTERLIGACAO_INTERNACIONAL.toString());
				txRef.setCos(interLig.getCosr());
				txRef.setDtRef(taxa.getPeriodo().getDataInicio().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
				txRef.setUsi(interLig.getNomeCurto());
				txRef.setUsiid(interLig.getId());
			}
			//Atualiza somente as taxas.
			if("TEIP".contains(taxa.getCodigo().toUpperCase())){
				txRef.setIp(taxa.getValor());
			}else{
				txRef.setTeif(taxa.getValor());
			}
			taxaRefRepository.save(txRef);
		}
	}

    /**
     *  Get all the taxaRefs.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    public List<TaxaRef> findAll() {
        log.debug("Request to get all TaxaRefs");
        Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        log.info("Authorities: " + Arrays.toString(authorities.toArray()));
        QTaxaRef ref= new QTaxaRef(TAXA_REF);
        return IterableConverter.toList(taxaRefRepository.findAll(ref.cos.in(mapAuthorities2COSR(authorities))));
    }
    
    //método para desmenbrar a complexidade de 'IF' do método abaixo para passar no sonar
    private void continuarVerficacao(GrantedAuthority auth, Collection<String> cRet){
    	if("ROLE_COSR_SE".equals(auth.getAuthority()) || COSR_SE.equals(auth.getAuthority())){
			
			cRet.add(COSR_SE);
			
		}else if("ROLE_COSR_NCO".equals(auth.getAuthority()) || COSR_NCO.equals(auth.getAuthority())){
			
			cRet.add(COSR_NCO);
			
		}else if("ROLE_CNOS".equals(auth.getAuthority()) || COSR_S.equals(auth.getAuthority())){
			
			cRet.add(COSR_NCO);
			cRet.add(COSR_NE);
			cRet.add(COSR_SE);
			cRet.add(COSR_S);
			
		}
	}
    private Collection<String> mapAuthorities2COSR(Collection<? extends GrantedAuthority> authorities) {
    	Collection<String> cRet = new ArrayList<>();
    	for (GrantedAuthority auth : authorities) {
    		if("ROLE_COSR_S".equals(auth.getAuthority()) || COSR_S.equals(auth.getAuthority())){
    			cRet.add(COSR_S);
    			
 			}else if("ROLE_COSR_NE".equals(auth.getAuthority()) || COSR_NE.equals(auth.getAuthority())){
 				
				cRet.add(COSR_NE);
				
			}else{
				continuarVerficacao(auth,cRet);
			}
			
    	}
    	return cRet;
    }
    
    public byte[] generateExcel(){
    	XSSFWorkbook workBook = new XSSFWorkbook();
    	
    	List<TaxaRef> taxasRef = this.findAll();
    	List<String> headers = new ArrayList<>();
    	headers.add("Usina");
    	headers.add("Mês de Referência");
    	headers.add("Teif");
    	headers.add("Ip");
    	
    	for (TaxaRef taxaRef : taxasRef) {
			XSSFSheet sheet = workBook.createSheet(taxaRef.getTipoInstalacao());
			int rownum = sheet.getLastRowNum();
			XSSFRow row = sheet.createRow(rownum++);
			
			//header - first line TODO clena coments
			int cellnum = 0;
			for (String header : headers) {
				XSSFCell cell = row.createCell(cellnum++);
				cell.setCellValue(header);
			}
			
			DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy/MM");
			
			//rest of lines
			row = sheet.createRow(rownum++);
			cellnum = 0;
			XSSFCell cell = row.createCell(cellnum++);
			cell.setCellValue(taxaRef.getUsi());
			cell.setCellValue((String) format.format(taxaRef.getDtRef()));
			cell.setCellValue(taxaRef.getTeif().toString());
			cell.setCellValue(taxaRef.getIp().toString());
			
		}
    	
    	ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			workBook.write(bos);
		} catch (IOException e) {
			throw new BadRequestException("Erro ao gerar Excel", "RN_XXXX_XX");
		} finally {
		    try {
				bos.close();
			} catch (IOException e) {
				throw new BadRequestException("Erro ao gerar Excel", "RN_XXXX_XX");
			}
		}
		
    	return bos.toByteArray();
    }

    /**
     *  Get one taxaRef by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    public TaxaRef findOne(String id) {
        log.debug("Request to get TaxaRef : {}", id);
        return taxaRefRepository.findOne(id);
    }
    
    public List<TaxaRef> getAllTaxaRefsByTipoInstalacao(String tipoInstalacao){
    	return IterableConverter.toList(taxaRefRepository.findAll(
    			qTaxaRef.tipoInstalacao.eq(tipoInstalacao)));
    } 

}
