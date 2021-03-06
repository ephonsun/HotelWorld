package edu.nju.hotel.data.repository;

import edu.nju.hotel.data.model.RoomAsign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by dzkan on 2016/3/8.
 */
@Repository
public interface RoomAsignRepository extends JpaRepository<RoomAsign, Integer> {
    @Query("select r.roomByRoomId.id from RoomAsign r where r.roomByRoomId.roomTypeByRoomTypeId.id=?1 and r.id not in (select r2.id from RoomAsign r2 where r2.inTime>=?3 or r2.outTime<=?2)")
    List<Integer> getAssignRoomBetween(int id, Date start, Date end);

    @Modifying
    @Transactional
    @Query("update RoomAsign asg set asg.state=1 , asg.checkinByCheckinId.id=?2 where asg.id=?1")
    void updateAssignChekin(int id,int checkinId);

    @Query("select asg from RoomAsign asg where asg.bookingByBookId.id=?1")
    List<RoomAsign> findByBookingId(int id);

    @Query("select ra from RoomAsign ra where ra.checkinByCheckinId.id=?1")
    List<RoomAsign> findByCheckin(int id);
}
