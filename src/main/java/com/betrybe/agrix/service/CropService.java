package com.betrybe.agrix.service;

import com.betrybe.agrix.exceptions.CropNotFoundException;
import com.betrybe.agrix.exceptions.FertilizerNotFoundException;
import com.betrybe.agrix.model.entities.Crop;
import com.betrybe.agrix.model.entities.Fertilizer;
import com.betrybe.agrix.model.repositories.CropRepository;
import com.betrybe.agrix.model.repositories.FertilizerRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

/**
 * Camada service de tudo que interage com a entidade crop.
 */
@Service
public class CropService {

  private CropRepository cropRepository;
  private FertilizerRepository fertilizerRepository;

  /**
   * Construtor da camada service.
   *
   * @param cropRepository Repositorio recebido por injeção
   *                       de dependencia
   */
  @Autowired
  public CropService(CropRepository cropRepository,
      FertilizerRepository fertilizerRepository) {

    this.cropRepository = cropRepository;
    this.fertilizerRepository = fertilizerRepository;

  }

  /**
   * Salva a Crop pela id da Farm.
   *
   * @param newCrop recebe uma nova crop a ser salva.
   * @return retorna a crop salva.
   */
  public Crop saveCropByFarmId(Crop newCrop) {

    Crop cropToCreate = this.cropRepository.save(newCrop);

    Optional<Crop> cropToFound = this.cropRepository.findById(cropToCreate.getId());

    Crop cropFound = cropToFound.get();

    return cropFound;

  }

  /**
   * Método que busca no banco de dados todas as plantacoes.
   *
   * @return retorna uma lista com todas as plantacoes.
   */
  public List<Crop> getAllCrops() {
    List<Crop> allCrops = this.cropRepository.findAll();

    return allCrops;
  }

  /**
   * Método que busca plantacao baseada no id.
   *
   * @param id recebe o id buscado.
   * @return retorna um opcional com a plantacao retornada.
   */
  public Crop getCropById(Long id) throws CropNotFoundException {
    Optional<Crop> cropToFound = this.cropRepository.findById(id);

    if (cropToFound.isEmpty()) {
      throw new CropNotFoundException();
    }

    return cropToFound.get();
  }

  /**
   * Retorna as plantacoes com a data de colheita entre as
   *     datas buscadas.
   *
   * @param startingDate data inicial da busca
   * @param endingDate data final da busca
   * @return retorna uma lista de plantacoes que atendem o requisito
   */
  public List<Crop> searchCropByDate(LocalDate startingDate, LocalDate endingDate) {

    List<Crop> allCrops = this.cropRepository.findAll();
    List<Crop> cropsReturned = allCrops.stream()
        .filter(crop -> startingDate.isBefore(crop.getHarverstDate())
            && endingDate.isAfter(crop.getHarverstDate()))
        .toList();

    return cropsReturned;
  }

  /**
   * Associa um fertilizante a uma plantaccao.
   *
   * @param cropId id da plantacao a ser associada
   * @param fertilizerId id do fertilizante a ser associado
   * @return retorna a plantacao atualizada com o fertilizante associado
   * @throws CropNotFoundException retorna um erro caso a plantacao nao
   *     seja encontrada
   * @throws FertilizerNotFoundException retorna um erro caso o fertilizante
   *     nao seja encontrado
   */
  public Crop setFertilizer(Long cropId, Long fertilizerId)
      throws CropNotFoundException, FertilizerNotFoundException {

    Optional<Crop> optionalCrop = this.cropRepository.findById(cropId);

    Optional<Fertilizer> optionalFertilizer =
        this.fertilizerRepository.findById(fertilizerId);

    if (optionalFertilizer.isEmpty()) {
      throw new FertilizerNotFoundException();
    }

    if (optionalCrop.isEmpty()) {
      throw new CropNotFoundException();
    }

    Crop cropFound = optionalCrop.get();
    Fertilizer fertilizerFound = optionalFertilizer.get();

    cropFound.getFertilizers().add(fertilizerFound);
    Crop newCrop = this.cropRepository.save(cropFound);
    return newCrop;
  }

}
