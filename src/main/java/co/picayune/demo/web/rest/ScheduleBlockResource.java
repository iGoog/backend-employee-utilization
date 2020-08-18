package co.picayune.demo.web.rest;

import co.picayune.demo.domain.ScheduleBlock;
import co.picayune.demo.repository.ScheduleBlockRepository;
import co.picayune.demo.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link co.picayune.demo.domain.ScheduleBlock}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ScheduleBlockResource {

    private final Logger log = LoggerFactory.getLogger(ScheduleBlockResource.class);

    private static final String ENTITY_NAME = "backendScheduleBlock";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ScheduleBlockRepository scheduleBlockRepository;

    public ScheduleBlockResource(ScheduleBlockRepository scheduleBlockRepository) {
        this.scheduleBlockRepository = scheduleBlockRepository;
    }

    /**
     * {@code POST  /schedule-blocks} : Create a new scheduleBlock.
     *
     * @param scheduleBlock the scheduleBlock to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new scheduleBlock, or with status {@code 400 (Bad Request)} if the scheduleBlock has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/schedule-blocks")
    public ResponseEntity<ScheduleBlock> createScheduleBlock(@Valid @RequestBody ScheduleBlock scheduleBlock) throws URISyntaxException {
        log.debug("REST request to save ScheduleBlock : {}", scheduleBlock);
        if (scheduleBlock.getId() != null) {
            throw new BadRequestAlertException("A new scheduleBlock cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ScheduleBlock result = scheduleBlockRepository.save(scheduleBlock);
        return ResponseEntity.created(new URI("/api/schedule-blocks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /schedule-blocks} : Updates an existing scheduleBlock.
     *
     * @param scheduleBlock the scheduleBlock to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated scheduleBlock,
     * or with status {@code 400 (Bad Request)} if the scheduleBlock is not valid,
     * or with status {@code 500 (Internal Server Error)} if the scheduleBlock couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/schedule-blocks")
    public ResponseEntity<ScheduleBlock> updateScheduleBlock(@Valid @RequestBody ScheduleBlock scheduleBlock) throws URISyntaxException {
        log.debug("REST request to update ScheduleBlock : {}", scheduleBlock);
        if (scheduleBlock.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ScheduleBlock result = scheduleBlockRepository.save(scheduleBlock);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, scheduleBlock.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /schedule-blocks} : get all the scheduleBlocks.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of scheduleBlocks in body.
     */
    @GetMapping("/schedule-blocks")
    public List<ScheduleBlock> getAllScheduleBlocks() {
        log.debug("REST request to get all ScheduleBlocks");
        return scheduleBlockRepository.findAll();
    }

    /**
     * {@code GET  /schedule-blocks/:id} : get the "id" scheduleBlock.
     *
     * @param id the id of the scheduleBlock to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the scheduleBlock, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/schedule-blocks/{id}")
    public ResponseEntity<ScheduleBlock> getScheduleBlock(@PathVariable Long id) {
        log.debug("REST request to get ScheduleBlock : {}", id);
        Optional<ScheduleBlock> scheduleBlock = scheduleBlockRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(scheduleBlock);
    }

    /**
     * {@code DELETE  /schedule-blocks/:id} : delete the "id" scheduleBlock.
     *
     * @param id the id of the scheduleBlock to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/schedule-blocks/{id}")
    public ResponseEntity<Void> deleteScheduleBlock(@PathVariable Long id) {
        log.debug("REST request to delete ScheduleBlock : {}", id);
        scheduleBlockRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
