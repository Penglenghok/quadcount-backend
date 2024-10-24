package backend.quadcount.controller;

import backend.quadcount.model.Group;
import backend.quadcount.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/groups")
@CrossOrigin(origins = "*")
public class GroupController {


    @Autowired
    private GroupService groupService;

    @GetMapping
    public List<Group> getAllGroups() {
        List<Group> groups =  groupService.getAllGroups();
        if (groups == null) {
            groups = new ArrayList<>();
        }
        return groups;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Group> getGroupById(@PathVariable String id) {
        Optional<Group> group = groupService.getGroupById(id);

        return group.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping()
    public ResponseEntity<Group> createGroup(@RequestBody Group group) {

        Group groupCreated = groupService.createGroup(group);
        return new ResponseEntity<Group>(groupCreated, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGroup(@PathVariable String id) {
        try {
            groupService.deleteGroup(id);
            return new ResponseEntity<>("Group deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);

        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Group>> getGroupsByUserId(@PathVariable Long userId) {
        List<Group> groups = groupService.getGroupsByUserId(userId);
        return ResponseEntity.ok(groups);
    }
}